(def +project+ 'it.frbracch/boot-marginalia)

(def +version+ "0.1.0")

(def +description+ "FIXME")

(def +source-paths+ #{"src/clj"})

(def +dependencies+ '[[marginalia "0.8.0"]])

(def +url+ "https://github.com/francesco-bracchi/boot-marginalia")

(def +license+ {"EPL" "http://www.eclipse.org/legal/epl-v10.html"})

(def +clojar+ "https://clojars.org/repo")

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
  push {:repo "deploy-clojars"})

(deftask build []
  (comp (sift) (pom) (jar)))

(defn get-username []
  (print "username: ")
  (read-line))

(defn get-password []
  (print "password: ")
  (apply str (-> (System/console) .readPassword)))

(defn get-credentials []
  (merge-env! :repositories
              [["deploy-clojars" {:url "https://clojars.org/repo"
                                  :username (get-username)
                                  :password (get-password)}]]))

(deftask deploy []
  (get-credentials)
  (print (:repositories (get-env)))
  (comp (build) (push)))
