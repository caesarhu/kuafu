# caesarhu/kuafu

A Clojure library designed to work with [OR-Tools](https://developers.google.com/optimization) from google.

## Usage

本專案仍處於早期開發階段，以下事項仍未完成：  
一、API文件未提供，請參考OR-Tools官方[參考文件](https://or-tools.github.io/docs/java/namespacecom_1_1google_1_1ortools_1_1sat.html)  
二、完整測試  
三、clojure OR-Tools的用法改進  
四、目前只支援Constraint Programming CP-SAT solver  

Clojure CLI/deps.edn
```edn
{:git/url "https://github.com/caesarhu/kuafu"
 :git/tag "v0.1.31"
 :git/sha "09e46d7"}
```

Using OR-tools SAT: 8-queens problem solver
```clojure
(require '[caesarhu.kuafu.sat :as sat])
(require '[clojure.string :as str])

(defn queens-solver
  [n]
  (let [model (sat/cp-model)
        solver (sat/cp-solver)
        domain (sat/domain 0 (dec n))
        queens (vec (repeatedly n #(sat/int-var model domain)))
        diag1 (map #(-> (sat/new-builder) (sat/add (queens %)) (sat/add %) sat/build) (range n))
        diag2 (map #(-> (sat/new-builder) (sat/add (queens %)) (sat/add (- %)) sat/build) (range n))
        make-row (fn [x]
                   (str/join " " (map #(if (= x %) "Q" "_") (range n))))
        make-table (fn [qv]
               (map make-row qv))]
    (sat/add-all-different model queens)
    (sat/add-all-different model diag1)
    (sat/add-all-different model diag2)
    (sat/set-all-solutions solver true)
    (reset! sat/*solutions* (list))
    (sat/solve solver model (sat/callback queens))
    (map make-table @sat/*solutions*)))
```


## License

Copyright © 2022 Caesar Hu

Distributed under the Eclipse Public License version 2.0.
