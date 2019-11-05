(ns pokedex.core
  (:require 
   [reagent.core :as r]
   [pokedex.db.core :as db]
   [pokedex.components.main :refer (main)]))

(defn start []
  (r/render-component [main]
                      (. js/document (getElementById "app"))))

(defn ^:export init []
  (db/init)
  (start))

(defn stop []
  (js/console.log "stop"))
