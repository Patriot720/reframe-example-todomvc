(ns todomvc.views.todos
  (:require [reagent.core  :as r]
            [clojure.string :as str]
            [todomvc.views.util :refer [todo-input]]
            [todomvc.views.tags :refer [tag-list]]
            [re-frame.core :refer [subscribe dispatch]])
            
  )

(defn todo-item
  []
  (let [editing (r/atom false)
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
