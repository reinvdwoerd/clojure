(ns sandbox.control-flow-test
  (:refer-clojure :exclude [ensure])
  (:use midje.sweet sandbox.control-flow))

(facts "for-fold works like reduce"
  (for-fold [sum 0
             n   (range 5 10)
             m   (range 10 20)]
    (+ sum n m)) => 95

  (for-fold [frequencies {}
             char        "hello world"]
    (update frequencies char (fnil inc 0))) => {\h 1, \e 1, \l 3, \o 2, \space 1, \w 1, \r 1, \d 1})

(facts "for-map merges the resulting seq of maps"
  (for-map [[k v] {:a 1 :b 2}
            [k2 v2] {:c 3 :d 4}]
    {k v k2 v2}) => {:a 1 :b 2 :c 3 :d 4})

(facts "do-let is just let where the bindings come last"
  (do-let
    :ignore-me
    (+ a b)
    [a 1 b 2]) => 3)

(facts "--> threads through the underscores"
  (--> 10 (/ _ 2)) => 5
  (--> 10 (/ 2 _)) => 1/5
  (--> 1 (+ 1) (* 2)) => 4
  (--> {:key :val} (:key)) => :val
  (--> {:key :val} :key) => :val)
