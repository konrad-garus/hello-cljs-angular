(ns hello-clojurescript)

(defn add-todo [scope]
  (fn []
    (.push (.-todos scope) (js-obj "text" (.-todoText scope) "done" false))
    (aset scope "todoText" "")))

(defn remaining [scope]
  (fn [] 
    (count (filter #(not (.-done %)) (.-todos scope)))))

(defn archive [scope]
  (fn []
    (let [arr (into-array (filter #(not (.-done %)) (.-todos scope)))]
      (aset scope "todos" arr  ))))

(defn CTodoCtrl [$scope]
  (def $scope.todos (array (js-obj "text" "learn angular" "done" true)))
  
  (def $scope.addTodo (add-todo $scope))
  
  ; These two are equivalent:
  ;
  ; (def $scope.remaining (remaining $scope))
  
  (aset $scope "remaining" 
        (fn []
          (count (filter #(not (.-done %)) (.-todos $scope)))))
  
  (def $scope.archive (archive $scope))) 

; These two are equivalent - see "Note on Minification" 
; at http://docs.angularjs.org/tutorial/step_05 
(comment
(def TodoCtrl CTodoCtrl)

(aset TodoCtrl "$inject" (array "$scope"))
)

(def TodoCtrl
  (array
    "$scope"
    CTodoCtrl))
