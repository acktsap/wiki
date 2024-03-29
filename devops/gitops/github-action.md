# Github Action

## Components

- Workflows
  - A workflow is a configurable automated process that will run one or more jobs. Workflows are defined by a YAML file checked in to your repository and will run when triggered by an event in your repository, or they can be triggered manually, or at a defined schedule.
- Events
  - An event is a specific activity in a repository that triggers a workflow run. For example, activity can originate from GitHub when someone creates a pull request, opens an issue, or pushes a commit to a repository. You can also trigger a workflow run on a schedule, by posting to a REST API, or manually.
- Jobs
  - A job is a set of steps in a workflow that execute on the same runner. Each step is either a shell script that will be executed, or an action that will be run. Steps are executed in order and are dependent on each other. Since each step is executed on the same runner, you can share data from one step to another.
- Actions
  - An action is a custom application for the GitHub Actions platform that performs a complex but frequently repeated task. Use an action to help reduce the amount of repetitive code that you write in your workflow files.
- Runners
  - A runner is a server that runs your workflows when they're triggered. Each runner can run a single job at a time.

## Workflow syntax

- [Workflow syntax for GitHub Actions](https://docs.github.com/en/actions/using-workflows/workflow-syntax-for-github-actions)
  - [on.<event_name>.types](https://docs.github.com/en/actions/using-workflows/events-that-trigger-workflows)

## Runners

- [Github-hosted runners](https://docs.github.com/en/actions/using-github-hosted-runners/about-github-hosted-runners)
  - ubuntu-latest

## Useful Actions

- [Checkout](https://github.com/actions/checkout)
- [Cache](https://github.com/actions/cache)
  - Cache for build
  - [Java Gradle Build Cache](https://github.com/actions/cache/blob/main/examples.md#java---gradle)
  - [Java Maven Local Repository Cache](https://github.com/actions/cache/blob/main/examples.md#java---maven)
- [CodeCov](https://github.com/marketplace/actions/codecov)
  - 이거 처음에 오픈이라도 안되는데 https://app.codecov.io/github/naver/spring-batch-plus 이렇게 https://app.codecov.io/github/${organization}/{repo}를 직접 치고 들어가니까 됨.
  - [kover coverage](https://lengrand.fr/kover-code-coverage-plugin-for-kotlin/)
- [github script action](https://github.com/actions/github-script)
  - make comment
  - apply label
  - ...
- [Gradle Build action](https://github.com/gradle/gradle-build-action)
  - gradle 버전 지정해서 실행 가능.
- [setup java](https://github.com/actions/setup-java)

## Tips

- [Checkout issue_comment](https://github.com/actions/checkout/issues/331)

## Event Payload

- [webhook events and payloads](https://docs.github.com/en/developers/webhooks-and-events/webhooks/webhook-events-and-payloads)
  - issues
  - issue_comment

Debugging

- Debuggin event details using toJson
  ```yaml
        - name: Echo event
          run: |
            echo "${{ toJson(github.event) }}"
  ```

## See also

- [github action (official)](https://docs.github.com/en/actions)
