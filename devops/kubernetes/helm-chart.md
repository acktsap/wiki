# Helm Chart

- [Concept](#concept)
- [Structure](#structure)
  - [Chart.yaml](#chartyaml)
- [Workload](#workload)
  - [Deployment](#deployment)
- [Tips](#tips)
- [See also](#see-also)

## Concept

- Helm은 kubernetes package manager.
- Helm chart는 helm이 관리하는 packaging format으로 kubernetes resource들의 집함. 
- Helo chart를 이용해서 Kubernetes cluster에 배포한 것을 Helm Release라고 부름.

> 중요한 것은 chart에서 제공하는 resource들의 언어. 즉, 이러이러한 리소스가 있고 이걸 위해서느 이렇게 한다.\
> 그리고 주로 차트는 이렇게 구성한다는 best practice.

## Structure

```sh
wordpress/
  Chart.yaml          # A YAML file containing information about the chart
  LICENSE             # OPTIONAL: A plain text file containing the license for the chart
  README.md           # OPTIONAL: A human-readable README file
  values.yaml         # The default configuration values for this chart
  values.schema.json  # OPTIONAL: A JSON Schema for imposing a structure on the values.yaml file
  charts/             # A directory containing any charts upon which this chart depends.
  crds/               # Custom Resource Definitions
  templates/          # A directory of templates that, when combined with values,
                      # will generate valid Kubernetes manifest files.
  templates/NOTES.txt # OPTIONAL: A plain text file containing short usage notes
```

### Chart.yaml

```sh
apiVersion: The chart API version (required)
name: The name of the chart (required)
version: A SemVer 2 version (required)
kubeVersion: A SemVer range of compatible Kubernetes versions (optional)
description: A single-sentence description of this project (optional)
type: The type of the chart (optional)
keywords:
  - A list of keywords about this project (optional)
home: The URL of this projects home page (optional)
sources:
  - A list of URLs to source code for this project (optional)
dependencies: # A list of the chart requirements (optional)
  - name: The name of the chart (nginx)
    version: The version of the chart ("1.2.3")
    repository: (optional) The repository URL ("https://example.com/charts") or alias ("@repo-name")
    condition: (optional) A yaml path that resolves to a boolean, used for enabling/disabling charts (e.g. subchart1.enabled )
    tags: # (optional)
      - Tags can be used to group charts for enabling/disabling together
    import-values: # (optional)
      - ImportValues holds the mapping of source values to parent key to be imported. Each item can be a string or pair of child/parent sublist items.
    alias: (optional) Alias to be used for the chart. Useful when you have to add the same chart multiple times
maintainers: # (optional)
  - name: The maintainers name (required for each maintainer)
    email: The maintainers email (optional for each maintainer)
    url: A URL for the maintainer (optional for each maintainer)
icon: A URL to an SVG or PNG image to be used as an icon (optional).
appVersion: The version of the app that this contains (optional). Needn't be SemVer. Quotes recommended.
deprecated: Whether this chart is deprecated (optional, boolean)
annotations:
  example: A list of annotations keyed by name (optional).
```

- apiVersion : v2 (for Helm3), v1 (previous, but still supported)
  - v1
    - A dependencies field defines chart dependencies, which were located in a separate requirements.yaml.
  - v2
    - A type discriminates application and library charts.
- appVersion
  - [SemVer 2 (Semantic Versioning 2)](https://semver.org/spec/v2.0.0.html)

## Workload

### Deployment

- [deployment (official)](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)

## Tips

- [Wrong interpretation of Zero in templates files when having a default value](https://github.com/helm/helm/issues/3164#issuecomment-709537506)

## See also

- [Helm Docs](https://helm.sh/docs/)