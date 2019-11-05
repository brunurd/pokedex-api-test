(ns pokedex.components.main
  (:require
   [pokedex.components.search-field :refer (search-field)]
   [pokedex.components.pokemon-data-table :refer (pokemon-data-table)]
   [pokedex.components.alert :refer (alert)]))

(defn main []
  [:main {:class "main"}
   [search-field]
   [alert]
   [pokemon-data-table]])