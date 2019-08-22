(ns todomvc.views.tags)
(defn tag-list [tags tag-editing]
  [:div {:class "tags"
         :on-double-click (fn []
                            (reset! tag-editing true))}

   (for [tag tags]
     ^{:key tag}
     [:div
      {:class "tag"
          ;; TODO add tag editing
          ; :on-double-click (fn []
          ;                     (println "double click")
          ;                     (reset! tag-editing true))}
       }
      tag
      ])])
