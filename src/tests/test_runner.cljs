(ns tests.test-runner
  (:require [cljs.test :refer-macros [run-tests]]
            [tests.core-test]))

;; This isn't strictly necessary, but is a good idea depending
;; upon your application's ultimate runtime engine.
(enable-console-print!)

(defn run-all-tests
  []
  (run-tests
   'tests.core-test))
