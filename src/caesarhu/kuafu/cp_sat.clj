(ns caesarhu.kuafu.cp-sat
  (:import [com.google.ortools Loader]
           [com.google.ortools.sat CpModel CpSolver CpSolverStatus IntVar CpSolverSolutionCallback]))

(Loader/loadNativeLibraries)

(defn new-model
  []
  (CpModel.))

(defn new-solver
  []
  (CpSolver.))

(defn solve
  [model vars]
  (let [solver (new-solver)
        status (str (.solve solver model))]
    {:values (map #(.value solver %) vars) :status status}))

(defn solutions
  [model vars]
  (let [counter (atom 0)
        values (atom [])
        cb (proxy [CpSolverSolutionCallback] []
             (onSolutionCallback []
               (swap! values conj (mapv #(.value this %) vars))
               (swap! counter inc)))
        solver (new-solver)
        _ (.. solver (getParameters) (setEnumerateAllSolutions true))
        status (str (.solve solver model cb))]
    {:count @counter :values @values :status status}))

(defn rand-letter-str
  [len]
  (transduce
   (map (fn [_]
          (let [rnd (rand-int 52)]
            (char (+ (mod rnd 26)
                     (int (if (< rnd 26) \a \A)))))))
   str
   ""
   (range len)))

(defn bool-var
  ([^com.google.ortools.sat.CpModel model ^java.lang.String name]
   (.newBoolVar model name))
  ([^com.google.ortools.sat.CpModel model]
   (.newBoolVar model (rand-letter-str 10))))

(defn constant
  [^com.google.ortools.sat.CpModel model ^long value]
  (.newConstant model value))

