(ns hello-cljs-angular.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resources]
            [ring.middleware.reload :as reload]
            [hiccup.core :as h]
            [hiccup.page :as hp]))

(defn render-body []
  (h/html 
    {:mode :html}
    (hp/doctype :html5)
    [:html {:ng-app ""}
     
     [:head]
     [:body
      [:h2 "Todo"]
      [:div {:ng-controller "hello_clojurescript.TodoCtrl"}
       [:span "{{remaining()}} of {{todos.length}} remaining"]
       "[" [:a {:href "" :ng-click "archive()"} "archive"] "]"
       [:ul.unstyled
        [:li {:ng-repeat "todo in todos"}
         [:input {:type "checkbox" :ng-model "todo.done"}]
         [:span {:class "done-{{todo.done}}"} "{{todo.text}}"]]]
       [:form {:ng-submit "addTodo()"}
        [:input {:type "text" :ng-model "todoText" :size "30" :placeholder "add new todo here"}]
        [:input.btn-primary {:type "submit" :value "add"}]]]
      
      (hp/include-js 
        "http://ajax.googleapis.com/ajax/libs/angularjs/1.0.2/angular.min.js"
        "js/cljs.js")
      (hp/include-css "css/todo.css")
      ]]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (render-body)})

(def app 
  (-> handler
    (resources/wrap-resource "public")
    (reload/wrap-reload)))

(defn -main [& args]
  (jetty/run-jetty app {:port 3000}))
