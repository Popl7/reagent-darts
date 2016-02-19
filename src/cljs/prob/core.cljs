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
;; Views
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
  [:div
   [:h2 "Home"]
   (menu-component)
   [:p "home!"]])

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

(defn current-page []
  [:div [(session/get :current-page)]])


;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")
(defroute "/" []
  (session/put! :current-page #'home-page))
(defroute "/highscores" []
  (session/put! :current-page #'highscores-page))
(defroute "/start" []
  (session/put! :current-page #'start-page))
(defroute "/stop" []
  (session/put! :current-page #'stop-page))


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
;; Initialize app
(defn mount-root []
  (r/render-component [current-page]
                      (.getElementById js/document "app")))

(defn ^:export main []
  (hook-browser-navigation!)
  (mount-root))

