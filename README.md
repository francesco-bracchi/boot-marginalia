[![Clojars Project](http://clojars.org/it.frbracch/boot-marginalia/latest-version.svg)](http://clojars.org/it.frbracch/boot-marginalia)

# Boot Marginalia

[Boot](https://github.com/boot-clj/boot) plugin for [marginalia](https://fogus.github.io/marginalia/)

## Try

run

```bash
boot -d it.frbracch/boot-marginalia marginalia target
```

> **NOTE:**
> boot versions prior to 2.5.0 [implicitely](https://github.com/boot-clj/boot/wiki/Target-Directory)
> create the target directory.
>
> In this case do not add the `target` task (i.e. `boot -d it.frbracch/boot-marginalia marginalia`)

## Usage

Add `boot-marginalia` to your `build.boot` dependencies and `require` the namespace `it.frbracch.boot-marginalia`

```clj
(set-env! :dependencies '[
  [it.frbracch/boot-marginalia "0.1.3-1" :scope "test"]
])

(require
  '[it.frbracch.boot-marginalia :refer [marginalia]])
```

For the complete options list, type `boot marginalia -h`

The resulting html file is placed in `docs/uberdoc.html`.

> NOTE: the fileset containing the document is passed through the rest of the
> task pipeline, therefore it is not readily present in the project root.
> Another task (e.g. `target`) should be used.

## License

Copyright © 2015 Francesco Bracchi

Licensed under Eclipse Public License.
