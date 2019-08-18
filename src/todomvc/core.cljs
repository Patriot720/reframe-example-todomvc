(ns todomvc.core
  (:require-macros [secretary.core :refer [defroute]])
  (:require [goog.events :as events]
            [reagent.core :as reagent]
            [re-frame.core :refer [dispatch dispatch-sync]]
            [secretary.core :as secretary]
            [todomvc.events]
            [todomvc.subs]
            [todomvc.views]
            [devtools.core :as devtools])
  (:import [goog History]
           [goog.history EventType]))

(devtools/install!)
(enable-console-print!)

(dispatch-sync [:initialise-db])

;;
(defroute "/" [] (dispatch [:set-showing :all]))
(defroute "/:filter" [filter] (dispatch [:set-showing (keyword filter)]))

(def history
  (doto (History.)
    (events/listen EventType.NAVIGATE
                   (fn [event] (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;;
(defn ^:export main
  [] (reagent/render [todomvc.views/todo-app]
       (.getElementById js/document "app")))
