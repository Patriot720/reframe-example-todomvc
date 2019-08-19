(ns tests.core-test
  (:require [cljs.test :refer-macros [deftest is]]
            [todomvc.subs :as subs]))

(def todos '({:tag "lul"}
             {}
             {:tag "lul"}))

(deftest tag-filtering
  (is (= (count (subs/select-tags todos)) 1))
  (is (= (first (subs/select-tags todos)) "lul")))

(deftest i-should-succeed
  (is (= 1 1)))
