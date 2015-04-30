(def +project+ 'it.frbracch/boot-marginalia)

(def +version+ "0.1.0")

(def +description+ "FIXME")

(def +source-paths+ #{"src/clj"})

(def +dependencies+ '[[marginalia "0.8.0"]
                      [adzerk/bootlaces "0.1.11" :scope "test"]])

(def +url+ "https://github.com/francesco-bracchi/boot-marginalia")

(def +repositories+ [["deploy-clojars" {:url "https://clojars.org/repo"}]])

(def +license+ {"EPL" "http://www.eclipse.org/legal/epl-v10.html"})

(def +clojar+ "https://clojars.org/repo")

(set-env!
 :source-paths +source-paths+
 :dependencies +dependencies+
 :repositories +repositories+)

(require '[adzerk.bootlaces :refer [bootlaces! push-snapshot push-release]])

(task-options!
  pom  {:project     +project+
        :version     +version+
        :description +description+
        :url         +url+
        :scm         {:url +url+}
        :license     +license+}
  sift {:to-resource #{#".*clj$"}}
  push {:repo "deploy-clojars"
        :ensure-clean true
        })

(bootlaces! +version+)

(deftask build []
  (comp (sift) (pom) (jar)))
