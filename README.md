# caesarhu/kuafu

A Clojure library designed to work with [OR-Tools](https://developers.google.com/optimization) from google.

## Usage

本專案仍處於早期開發階段，以下事項仍未完成：  
一、版權聲明  
二、API文件  
三、完整測試  
四、clojure OR-Tools的用法改進

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
