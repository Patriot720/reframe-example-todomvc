(ns tests.core-test
  (:require [cljs.test :refer-macros [deftest is]]
            [todomvc.subs :as subs]
           [todomvc.events :as events] ))

(def todos '({:id "nice" :tags ["lul" "wasas"]}
             {}
             {:id "nul":tags ["lul" "wasasz"]}))
(def other-todos {1 {:id 1 :tags ["lul" "wasas"]}
                  2 {:id 2 :tags ["nice" "wass"]}})

(deftest tag-filtering
(let [todos (subs/select-tags todos)]
  (is (= (count todos) 3))
  (is (= todos '("lul" "wasas" "wasasz")))))

; (fn [todos [_ id tag]]
;    (update-in todos [id :tags] #(conj %1 %2)))

(deftest add-tag-test
  (is (= (events/add-tag other-todos ["" 1 "sometag"])
         {1 {:id 1 :tags ["lul" "wasas" "sometag"]}
          2 {:id 2 :tags ["nice" "wass"]}})))
(deftest delete-tag-test
  (is (= (:tags (get (events/delete-tag other-todos [nil 1 "wasas"]) 1))
         ["lul"])))

(deftest edit-tag-test
  (is (= (-> (events/edit-tag other-todos [nil 1 "wasas" "nice"])
             (get 1)
             :tags
             last)
         "nice")))


(deftest i-should-succeed
  (is (= 1 1)))
;; TODO test and refactor