(ns todomvc.views.tags
(:require [reagent.core  :as r]
          [todomvc.views :as views]
          [re-frame.core :refer [subscribe dispatch]])
  )
(defn tag-list [id tags]
  (let [tag-adding (r/atom false)]
    [:div
     [:ul {:class "tags"
           :on-double-click (fn []
                              (reset! tag-adding true)
                              (println @tag-adding))}
      (for [tag tags]
        ^{:key tag}
        [:li
         {:class "tag"}
         tag])]
     (when @tag-adding
       [:h3 "WTF"])]))
