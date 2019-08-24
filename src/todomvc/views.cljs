(ns todomvc.views
  (:require [reagent.core  :as reagent]
            [re-frame.core :refer [subscribe dispatch]]
            [todomvc.views.tags :refer [tag-list]]
            [todomvc.views.util :refer [todo-input]]
            [clojure.string :as str]))


(defn todo-item
  []
  (let [editing (reagent/atom false)
        ]
    (fn [{:keys [id done title tags]}]
      [:li {:class (str (when done "completed ")
                        (when @editing "editing"))}
       [:div.view
        [:input.toggle
         {:type "checkbox"
          :checked done
          :on-change #(dispatch [:toggle-done id])}]
        [:label
         {:on-double-click #(reset! editing true)}
         title]
        [tag-list id tags]
        [:button.destroy
         {:on-click #(dispatch [:delete-todo id])}]]
       (when @editing
         [todo-input
          {:class "edit"
           :title title
           :on-save #(if (seq %)
                       (dispatch [:save id %])
                       (dispatch [:delete-todo id]))
           :on-stop #(reset! editing false)}])])))

(defn task-list
  []
  (let [visible-todos @(subscribe [:visible-todos])
        all-complete? @(subscribe [:all-complete?])]
    [:section#main
     [:input#toggle-all
      {:type "checkbox"
       :checked all-complete?
       :on-change #(dispatch [:complete-all-toggle])}]
     [:label
      {:for "toggle-all"}
      "Mark all as complete"]
     [:ul#todo-list
      (for [todo  visible-todos]
        ^{:key (:id todo)} [todo-item todo])]]))

(defn footer-controls
  []
  (let [[active done] @(subscribe [:footer-counts])
        showing       @(subscribe [:showing])
        tags          @(subscribe [:tags])
        a-fn          (fn [filter-kw txt]
                        ;; TODO keyword and just a string don't match
                        [:a {:class (when (= (name filter-kw) (name showing)) "selected") ;; TODO fix for tags
                             :href (str "#/" (name filter-kw))} txt])]
    [:footer#footer
     [:span#todo-count
      [:strong active] " " (case active 1 "item" "items") " left"]
     [:ul#filters
      [:li (a-fn :all    "All")]
      [:li (a-fn :active "Active")]
      [:li (a-fn :done   "Completed")]
      (for [tag tags]
        (if tag
          ^{:key tag} 
          [:li  (a-fn tag tag)]))
      ]
     (when (pos? done)
       [:button#clear-completed {:on-click #(dispatch [:clear-completed])}
        "Clear completed"])]))

(defn task-entry
  []
  [:header#header
   [:h1 "todos"]
   [todo-input
    {:id "new-todo"
     :placeholder "What needs to be done?"
     :on-save #(when (seq %)
                 (dispatch [:add-todo %]))}]])

(defn todo-app
  []
  [:div
   [:section#todoapp
    [task-entry]
    (when (seq @(subscribe [:todos]))
      [task-list])
    [footer-controls]]
   [:footer#info
    [:p "Double-click to edit a todo"]]])
