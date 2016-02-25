(def +project+ 'it.frbracch/boot-marginalia)

(def +version+ "0.1.3-1")

(def +description+ "boot plugin for marginalia")

(def +source-paths+ #{"src/clj"})

(def +dependencies+ '[[boot/core           "2.1.2" :scope "provided"]
                      [org.clojure/clojure "1.6.0" :scope "provided"]
                      [michaelblume/lein-marginalia "0.9.0"]])

(def +url+ "https://github.com/francesco-bracchi/boot-marginalia")

(def +license+ {"EPL" "http://www.eclipse.org/legal/epl-v10.html"})

(set-env!
 :source-paths +source-paths+
 :dependencies +dependencies+)

(task-options!
  pom  {:project     +project+
        :version     +version+
        :description +description+
        :url         +url+
        :scm         {:url +url+}
        :license     +license+}
  sift {:to-resource #{#".*clj$"}}
  push {:repo "deploy-clojars"
        :gpg-sign true})

(defn get-username []
  (print "username: ")
  (read-line))

(defn get-password []
  (print "password: ")
  (apply str (-> (System/console) .readPassword)))

(deftask clojars-credentials
  []
  (fn [next]
    (fn [fileset]
      (boot.util/info "Clojar credentials\n")
      (merge-env! :repositories
                  [["deploy-clojars" {:url "https://clojars.org/repo"
                                      :username (get-username)
                                      :password (get-password)}]])
      (println (get-env))
      (next fileset))))

(deftask pack
  "pack file in a jarfile"
  []
  (comp (pom) (jar)))

(deftask build
  "build and pack the jarfile"
  []
  (comp (sift) (pack)))

(deftask deploy-clojars
  "deploy to clojars"
  []
  (comp (clojars-credentials) (push)))

(deftask release []
  (comp (build) (deploy-clojars)))

(deftask local []
  (comp (build) (install)))
