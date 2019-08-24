(ns todomvc.util.tags
  (:require [reagent.core  :as r]
            [todomvc.views.util :as util]
            [re-frame.core :refer [subscribe dispatch]]))

(defn tag-list []
  (let [tag-adding (r/atom false)]
    (fn [id tags]
      [:div
       [:ul.tags {:on-double-click (fn []
                                     (reset! tag-adding true))}
        (for [tag tags]
          ^{:key tag}
          [:li.tag tag])]
       (when @tag-adding
         [util/todo-input
          {:class "tag-add"
           :on-save #(dispatch [:add-tag id %])
           :on-stop #(reset! tag-adding false)}])])))
