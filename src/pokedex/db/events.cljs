(ns pokedex.db.events
  (:require
   [clojure.walk :as walk]
   [re-frame.core :as rf]
   [pokedex.db.db :refer (initial-db)]))

(rf/reg-event-db
 :initialize
 initial-db)

(rf/reg-fx
 :fetch
 (fn [{:keys [url query variables on-success on-error]}]
   (let [body (-> {:query     query
                   :variables variables}
                  clj->js
                  js/JSON.stringify)]
     (-> (js/fetch url
                   (clj->js {:method  "POST"
                             :headers {"Content-Type" "application/json"}
                             :body    body}))
         (.then (fn [res]
                  (if-not (.-ok res)
                    (throw (js/Error (.-statusText res)))
                    (.json res))))
         (.then (fn [json]
                  (let [{:keys [data errors]} (-> json
                                                  js->clj
                                                  walk/keywordize-keys)]
                    (if errors
                      (rf/dispatch [on-error errors])
                      (rf/dispatch [on-success data])))))
         (.catch (fn [errors]
                   (rf/dispatch [on-error errors])))))))

(rf/reg-event-fx
 :find-pokemon
 (fn [_ [_ name]]
   {:fetch {:url "https://graphql-pokemon.now.sh/"
            :query "query($name: String!) { pokemon (name: $name) { id, number, name } }"
            :variables {:name name}
            :on-success :add-pokemon
            :on-error :add-error}}))

(rf/reg-event-fx
 :add-pokemon
 (fn [{:keys [db]} [_ data]]
   (if (empty? (:pokemon data))
     {:db (assoc db :error "Cannot find this pokemon!")}
     {:db (-> db
              (assoc :pokemons (conj (:pokemons db) (:pokemon data)))
              (assoc :error ""))})))

(rf/reg-event-fx
 :add-error
 (fn [{:keys [db]} [_ error]]
   {:db (assoc db :error error)}))

(rf/reg-event-fx
 :clean-errors
 (fn [{:keys [db]} [_ _]]
   {:db (conj db :error "")}))