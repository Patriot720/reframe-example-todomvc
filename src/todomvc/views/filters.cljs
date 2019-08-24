(ns todomvc.views.filters
  (:require [reagent.core  :as r]
            [todomvc.views.util :as util]
            [re-frame.core :refer [subscribe dispatch]]))

(defn- a-fn [showing filter-kw txt]
                        ;; TODO keyword and just a string don't match
  [:a {:class (when (= (name filter-kw) (name showing)) "selected") ;; TODO fix for tags
       :href (str "#/" (name filter-kw))} txt])

(defn filters []
  (let [tags @(subscribe [:tags])
        showing @(subscribe [:showing])
        a-fn (partial a-fn showing)]
    [:ul#filters
     [:li (a-fn :all    "All")]
     [:li (a-fn :active "Active")]
     [:li (a-fn :done   "Completed")]
     (for [tag tags]
       (if tag
         ^{:key tag}
         [:li  (a-fn tag tag)]))]))