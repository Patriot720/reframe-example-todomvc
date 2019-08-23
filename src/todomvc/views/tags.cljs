(ns todomvc.views.tags
(:require [reagent.core  :as r]
          [todomvc.views :as views]
          [re-frame.core :refer [subscribe dispatch]])
  )
(defn tag-list []
  (let [tag-adding (r/atom false)]
    (fn [id tags]
      [:div
       [:ul {:class "tags"
             :on-double-click (fn []
                                (reset! tag-adding true))}
        (for [tag tags]
          ^{:key tag}
          [:li
           {:class "tag"}
           tag])]
       (when @tag-adding
         [views/todo-input
          {:class "tag-add"
           :on-save #(dispatch [:add-tag id %])
           :on-stop #(reset! tag-adding false)}])])))
