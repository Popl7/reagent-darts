(ns prob.core
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [ajax.core :as ajax]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [reagent.core :as r]
            [reagent.session :as session]
            [secretary.core :as secretary]))

(enable-console-print!)

;; -------------------------
;; State
(def app-state (r/atom {}))

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")
(defroute "/" []
  (swap! app-state assoc :page :home))
(defroute "/highscores" []
  (swap! app-state assoc :page :highscores))
(defroute "/start" []
  (swap! app-state assoc :page :start))
(defroute "/stop" []
  (swap! app-state assoc :page :stop))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Pages
(defn menu-component []
  [:div.row
   [:a.col-xs-3.col-md-2.btn.btn-default {:role "button"
                                          :href "#/highscores"}
    [:i.fa.fa-shield] " Scores"]
   [:a.col-xs-3.col-md-2.btn.btn-default {:role "button"
                                          :href "#/start"}
    [:i.fa.fa-thumbs-o-up] " Start"]
   [:a.col-xs-3.col-md-2.btn.btn-default {:role "button"
                                          :href "#/stop"}
    [:i.fa.fa-rocket] " Stop"]
   [:a.col-xs-3.col-md-2.btn.btn-default {:role "button"
                                          :href "#/"}
    [:i.fa.fa-twitter] " Home"]])

(defn home-page []
  (let [page (get @app-state :page)]
    [:div
     [:h2 "Home"]
     (menu-component)
     [:p (str "page: " page)]]))

(defn highscores-page []
  [:div
   [:h2 "Scores"]
   (menu-component)])

(defn start-page []
  [:div
   [:h2 "Start"]
   (menu-component)])

(defn stop-page []
  [:div
   [:h2 "Stop"]
   (menu-component)])

;; -------------------------
;; App-state
(defmulti current-page #(@app-state :page))
(defmethod current-page :home []
  [home-page])
(defmethod current-page :highscores []
  [highscores-page])
(defmethod current-page :start []
  [start-page])
(defmethod current-page :stop []
  [stop-page])
(defmethod current-page :default []
  [home-page])

;; -------------------------
;; Initialize app
(defn mount-root []
  (r/render-component [current-page]
                      (.getElementById js/document "app")))

(defn ^:export main []
  (hook-browser-navigation!)
  (mount-root))

