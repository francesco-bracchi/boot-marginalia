[![Clojars Project](http://clojars.org/it.frbracch/boot-marginalia/latest-version.svg)](http://clojars.org/it.frbracch/boot-marginalia)

# Boot Marginalia

[Boot](https://github.com/boot-clj/boot) plugin for [marginalia](https://fogus.github.io/marginalia/)

## Try

run 

```bash
boot -d it.frbracch/boot-marginalia marginalia
```

## Usage

Add `boot-marginalia` to your `build.boot` dependencies and `require` the namespace `it.frbracch.boot-marginalia`

```clj
(set-env! :dependencies '[
  [it.frbracch/boot-marginalia "0.1.3" :scope "test"]
])

(require
  '[it.frbracch.boot-marginalia :refer [marginalia]])
```

For the complete list of the options `boot marginalia -h`

The resulting html file can be found in `target/docs/uberdoc.html`

## License

Copyright © 2015 Francesco Bracchi

Licensed under Eclipse Public License.
