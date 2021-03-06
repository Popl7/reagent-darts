(defproject prob "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0-RC3"]
                 [org.clojure/clojurescript "1.7.170"]
                 [ring "1.4.0"]
                 [compojure "1.4.0"]
                 [ring-transit "0.1.4"]
                 [cljs-ajax "0.5.1"]
                 [reagent "0.5.1"]
                 [reagent-utils "0.1.4"]
                 [secretary "1.2.3"]]

  :plugins [[lein-cljsbuild "1.1.1"]]

  :source-paths ["src/clj" "src/cljs"]
  :test-paths ["test/clj" "test/cljs"]
  :resource-paths ["resources"]

  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [figwheel-sidecar "0.5.0-2"]]
                   :source-paths ["dev/clj" "dev/cljs"]
                   :resource-paths ["dev/resources"]}
             :uberjar {:main prob.core
                       :omit-source true
                       :aot :all
                       :source-paths ["src/clj" "src/cljs"]
                       :uberjar-name "prob.jar"}}
  :template-additions [".gitignore" "CHANGELOG.md" "resources/public/index.html"]
  :cljsbuild {:builds
              {:dev {:source-paths ["dev/cljs" "src/cljs"]
                     :figwheel {:on-jsload prob.dev/on-jsload}
                     :compiler {:output-to "resources/public/js/app.js"
                                :source-map "resources/public/js/app.js"
                                :asset-path "js/out"
                                :main prob.core
                                :optimizations :none}}
               :prod
               {:source-paths ["src/cljs"]
                :jar true
                :compiler {:output-to "resources/public/js/app.js"
                           :asset-path "js/out"
                           :main prob.core
                           :optimizations :advanced
                           :pretty-print false}}}}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :figwheel {:css-dirs ["resources/public/css"]}
  :clean-targets ^{:protect false} [:target-path])
