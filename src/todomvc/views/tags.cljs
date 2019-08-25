(ns todomvc.views.tags
  (:require [reagent.core  :as r]
            [todomvc.views.util :as util]
            [re-frame.core :refer [subscribe dispatch]]))

(defn tag []
  (let [tag-editing (r/atom false)]
    (fn [title todo-id]
      [:li.tag
       {:on-double-click
        (fn [event]
          (.stopPropagation event)
          (reset! tag-editing true))}
       title
       (when @tag-editing (println "NICE"))
       (when @tag-editing
         [util/todo-input
          {:class "tag-editing"
           :title title
           :on-save #(if (seq %) (dispatch [:edit-tag todo-id title %]))
           :on-stop #(reset! tag-editing false)}])
       [:button.delete-tag
        {:on-click
         #(dispatch [:delete-tag todo-id title])} "x"]])))

(defn tag-list []
  (let [tag-adding (r/atom false)]
    (fn [todo-id tags]
      [:div
       [:ul.tags {:on-double-click (fn []
                                     (reset! tag-adding true))}
        (for [tag-title tags]
          ^{:key (gensym tag-title)}
          [tag tag-title todo-id])]
       (when @tag-adding
         [util/todo-input
          {:class "tag-add"
           :on-save #(if (seq %) (dispatch [:add-tag todo-id %]))
           :on-stop #(reset! tag-adding false)}])])))