(ns pokedex.components.pokemon-data-table
  (:require 
   [clojure.string :as string]
   [re-frame.core :as rf]))

(defn pokemon-data-table []
  (let [pokemons @(rf/subscribe [:pokemons])
        pokemon (get-in pokemons [0])]
    (when (not (string/blank? (:name pokemon)))
      [:table {:class "pokemon-data-table" :border 1}
       [:thead
        [:tr (map (fn [key]
                    ^{:key key} [:th (name key)])
                  (keys pokemon))]]
       [:tbody
        (map (fn [pokemon]
               ^{:key pokemon} [:tr (map (fn [val]
                                           ^{:key val} [:td val])
                                         (vals pokemon))]) pokemons)]])))