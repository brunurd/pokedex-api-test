(ns pokedex.core
  (:require [reagent.core :as r]))

(defn hello-world []
  [:ul 
   (map (fn [item]
          ^{:key item} [:li]) [0 1 2])])

(defn start []
  (r/render-component [hello-world]
                            (. js/document (getElementById "app"))))

(defn ^:export init []
  (start))

(defn stop []
  (js/console.log "stop"))
