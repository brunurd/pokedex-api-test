(ns pokedex.components.search-field
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]))

(def pokemon-name (r/atom ""))

(defn search-field []
  (let [on-change-handler (fn [e]
                            (let [value (-> e .-target .-value)]
                              (reset! pokemon-name value)))
        on-submit-handler (fn [e]
                            (.preventDefault e)
                            (when (not (= @pokemon-name ""))
                              (rf/dispatch [:find-pokemon @pokemon-name])
                              (reset! pokemon-name "")))]
    [:form {:class "search-field"
            :onSubmit on-submit-handler}
     [:input {:type "text"
              :placeholder "Type a pokemon name here..."
              :value @pokemon-name
              :on-change on-change-handler}]
     [:button {:type "submit"} "Search"]]))