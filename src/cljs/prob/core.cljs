(ns prob.core
    (:require [reagent.core :as r]
              [ajax.core :as ajax]))

(enable-console-print!)

(defn root-component []
  (let [loaded-stuff (r/atom nil)]
    (ajax/GET "/load-stuff"
              {:handler (fn [data]
                          (reset! loaded-stuff data))})
    (fn []
      [:div.row
       [:div.col-xs-6
        [:h1 "501"]]
       [:div.col-xs-6
        [:h1 "501"]]
       #_[:span "Loaded stuff in the background2:" (str @loaded-stuff)]])))

(defn ^:export main []
  (r/render-component
   [root-component]
   (js/document.getElementById "app")))
