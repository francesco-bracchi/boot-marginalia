(ns it.frbracch.boot-marginalia
  "marginalia plugin for the boot build system"
  {:boot/export-tasks true}
  (:require [boot.core :as core :refer [deftask]]
            [boot.util :as util]
            [boot.task.built-in :as builtin]
            [marginalia.core :as marg]
            [marginalia.html :refer [*resources*]]))

(defn pom-option
  "get a value from pom task options"
  [k]
  (-> builtin/pom var meta :task-options k))

(defn boot-marginalia?
  "test if a dependency is `boot-marginalia`"
  [dep]
  (= (first dep) 'it.frbracch/boot-marginalia))

(defn scope
  "get the scope of a dependency according to mvn dependency scope"
  [dep]
  (cond
    (empty? dep) "compile"
    (= (first dep) :scope) (second dep)
    :else (recur (rest dep))))

(defn development-dep?
  "test if a dependency is part of the `dev-depenencies` set"
  [dep]
  (-> dep scope #{"test" "provided"}))

(defn runtime-dep?
  "test if a dependency is runtime or not (or is boot-marginalia itself)"
  [dep]
  (not (or (boot-marginalia? dep)
           (development-dep? dep))))

(defn env-dependencies
  ([] (-> (core/get-env) :dependencies env-dependencies))
  ([env] (filter runtime-dep? env)))

(deftask marginalia
  "Run Marginalia against your project source files"
  [d dir     VAL str     "Directory into which the documentation will be written"
   f file    VAL str     "File into which the documentation will be written"
   n name    VAL sym     "Project name - if not given will be taken from pom task options"
   v version VAL str     "Project version - if not given will be taken from pom task options"
   e desc    VAL str     "Project description - if not given will be taken from pom task options"
   c css     VAL #{str}  "Additional css resources"
   j js      VAL #{str}  "Additional javascript resources"
   m multi       bool    "Generate each namespace documentation as a separate file"
   p deps    VAL edn     "Project dependencies - if not given will be taken from the env, filtering out dependencies in 'test' and 'provided' scopes"]
  (fn [next]
    (fn [fileset]
      (let [tgt       (core/tmp-dir!)
            dir       (or dir "docs")
            file      (or file "uberdoc.html")
            name      (or name (pom-option :project))
            version   (or version (pom-option :version))
            desc      (or desc (pom-option :description))
            deps      (or deps (env-dependencies))
            full-dir  (str (.getPath tgt) "/" dir)
            full-file (str full-dir "/" file)
            sources   (-> (core/get-env) :source-paths seq marg/format-sources distinct)
            opts      {:name         name
                       :version      version
                       :description  desc
                       :dependencies deps
                       :multi        multi
                       :marginalia   {:css css :javascript js}}]
        (core/empty-dir! tgt)
        (util/info "Generating Marginalia documentation for the following source files:\n")
        (doseq [s sources] (util/info (str "   " s "\n")))
        (marg/ensure-directory! full-dir)
        (binding [*resources* ""]
          (if multi
            (marg/multidoc! full-dir sources opts)
            (marg/uberdoc!  full-file sources opts)))
        (util/info (str "Done generating your documentation in " dir "\n"))
        (-> fileset (core/add-resource tgt) core/commit! next)))))
         
