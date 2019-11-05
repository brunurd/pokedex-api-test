(ns pokedex.components.alert
  (:require 
   [re-frame.core :as rf]))

(defn alert []
  (let [error @(rf/subscribe [:error])]
    [:p {:style {:color "red"}} error]))