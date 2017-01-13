(ns db.core
  (:use midje.sweet)
  (:require [db.core :refer [transition event]]))

(defn transition-event [& args]
  (transition #{} (apply event args)))

(facts "about event"
  (event :assert {:color :blue}) => (contains {:type :assert :attributes {:color :blue}})
  (event :retract [:drink] :where {:drink :tea}) => (contains {:where {:drink :tea}}))

(facts "about transition"
  ; Insert
  (transition-event :assert {:color :blue}) => #{{:color :blue}}
  ; Update
  (transition #{{:drink :tea}} (event :assert {:drink :coffee} :where {:drink :tea})) => #{{:drink :coffee}}
  (transition #{{:age 4 :name "Lieuwe"}} (event :assert {:age 5} :where {:name "Lieuwe"})) => #{{:age 5 :name "Lieuwe"}}
  ; Remove Attributes
  (transition #{{:age 4 :name "Lieuwe"}} (event :retract [:age])) => #{{:name "Lieuwe"}}
  ; Remove
  (transition #{{:color :blue} {:color :yellow}} {:type :retract :where {:color :yellow}}) => #{{:color :blue}})