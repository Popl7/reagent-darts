(ns prob.server
  (:require [ring.util.response :as response]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET PUT POST]]
            [ring.middleware.transit :as transit]
            [ring.adapter.jetty :as jetty])
  (:import [org.eclipse.jetty.server Server]))

(def server (atom nil))

(defn highscores []
  [
   ["steven" 10]
   ["davide" 8]])

(defroutes routes
  (GET "/" req (response/resource-response "index.html" {:root "public"}))
  (GET "/load-stuff" [] {:status 200
                         :body {:something "here2"}})
  (GET "/highscores" [] {:status 200
                         :body (highscores)})
  (route/resources "/")
  (route/not-found "Not found"))

(def app
  (-> routes
      (transit/wrap-transit-response {:encoding :json})
      (transit/wrap-transit-body)))

(defn start! []
  (when-not @server
    (reset! server (jetty/run-jetty #'app {:join? false :port 8080}))))

(defn stop! []
  (when-let [s ^Server @server]
    (println "Stopping server")
    (.stop @server)
    (reset! server nil)))
