(ns pokedex.db.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :pokemons
  (fn [db _]
    (:pokemons db)))

(rf/reg-sub
 :error
 (fn [db]
   (:error db)))
