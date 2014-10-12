(defproject cljs-memory-game "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [prismatic/dommy "0.1.3"]
                 [org.clojure/clojurescript "0.0-2311"]
                 [org.clojure/core.async "0.1.256.0-1bf8cf-alpha"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "cljs-memory-game"
              :source-paths ["src"]
              :compiler {
                :output-to "cljs_memory_game.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})
