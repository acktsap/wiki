# Jekyll

## What is it

- A static site generator written in ruby.
- Uses [liquid](https://shopify.github.io/liquid/) template language.

## Site Structure

index.html uses

```yml
```

- _config.yml store config data.
- _drafts : unpublished posts.
  - `title.MARKUP`
- _includes : partials can be used in layouts.
  - `{% include file.ext %}` uses `_includes/file.ext.`
- _layouts : templates that wrap posts.
  - used as
    ```yml
    ---
    layout: post # here
    title: Blogging Like a Hacker
    ---
    ```
- _posts : a content.
  - should follow `YEAR-MONTH-DAY-title.MARKUP` format.
- _data : site data.
  - `site.data.members` uses `_data/membersl.yml`.
- _sass : sass partials can be imported in `main.scss`.
- _site : generated static directory. add it to `.gitignore`.
- .jekyll-metadata : keep track of which files have not been modified since the last built. add it to `.gitignore`.
- index.html or index.md in root directory : home.
- *.html or *.md in root directory : to be transformed by Jekyll.

## Comment

- [disqus](https://disqus.com/)

## TroubleShooting

### Ruby 3.0에서 jekyll이 깨지는 경우

https://github.com/jekyll/jekyll/issues/8523

webrick이 3.0부터 기본적으로 없어서 그럼

```sh
> bundle add webrick
```

## References

- [Github 블로그 만들기 (1)](https://velog.io/@zawook/Github-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%A7%8C%EB%93%A4%EA%B8%B0-1)
- [Github 블로그 만들기 (2)](https://velog.io/@zawook/Github-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%A7%8C%EB%93%A4%EA%B8%B0-2)
- [jekyll docs](https://jekyllrb.com/docs/)