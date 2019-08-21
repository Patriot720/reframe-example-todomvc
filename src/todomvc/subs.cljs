(ns todomvc.subs;

  (:require [re-frame.core :refer [reg-sub subscribe]]))

;;
(reg-sub
 :showing
 (fn [db _]
   (:showing db)))

(defn sorted-todos
  [db _]
  (:todos db))
(reg-sub :sorted-todos sorted-todos)

;;
(reg-sub
 :todos (fn [query-v _]
          (subscribe [:sorted-todos]));;
 (fn [sorted-todos query-v _]
   (vals sorted-todos)))

#_(reg-sub
   :visible-todos (fn [query-v _]
                    [(subscribe [:todos])
                     (subscribe [:showing])]) (fn [[todos showing] _]
                                                (let [filter-fn (case showing
                                                                  :active (complement :done)
                                                                  :done   :done
                                                                  :all    identity)]
                                                  (filter filter-fn todos))))

(defn tag-equals [todo])
(reg-sub
 :visible-todos
 :<- [:todos]
 :<- [:showing]
 (fn [[todos showing] _]
   (let [filter-fn (case showing
                     :active (complement :done)
                     :done   :done
                     :all    identity
                     (fn [todo]
                       (some #{(name showing)} (:tags todo) )))]
     (filter filter-fn todos))))

     ; TODO add multiple tags for a single item
     ; TODO refactor some stuff
     ; TODO git flow
     ; TODO add tags deletion
     ; TODO fix theme
     ; TODO add mui tags maybe
(defn select-tags [todos & args]
  (->> (reduce (fn [item1 item2]
                 (concat item1 (:tags item2))) [] todos)
       (filter some?)
       distinct
       ))

(reg-sub
 :tags
 :<- [:todos]
 select-tags)

(reg-sub
 :all-complete?
 :<- [:todos]
 (fn [todos _]
   (every? :done todos)))

(reg-sub
 :completed-count
 :<- [:todos]
 (fn [todos _]
   (count (filter :done todos))))

(reg-sub
 :footer-counts
 :<- [:todos]
 :<- [:completed-count]
 (fn [[todos completed] _]
   [(- (count todos) completed) completed]))
