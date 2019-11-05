(ns pokedex.db.core
  (:require 
   [re-frame.core :as rf]
   [pokedex.db.events]
   [pokedex.db.subs]))

(defn init []
  (rf/dispatch-sync [:initialize]))