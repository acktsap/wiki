# Amazon Web Service

## Tips

### ec2 Maintanence page

![ec2-maintenance](./img/ec2-maintenance.png)

Load Balancers -> Add listener returning fixed response 503

필요시 port binding을 443으로 하면 maintenance page가 나옴

Content 예제

```html
<!doctype html>
<html lang="en">
  <head>
    <title>Site Maintenance</title>
    <meta charset="utf-8"/>
    <meta name="msapplication-TileColor" content="#da532c">
    <meta name="theme-color" content="#ffffff">
    <style>
      body {
        font: 20px Helvetica, sans-serif;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      body article {
        margin: 0 auto;
      }
      .title {
        font-size: 50px;
        text-align: center;
        padding-bottom: 20px;
      }
      .content {
        font-size: 30px;
        text-align: center;
      }
    </style>
  </head>
  <body>
    <div>
      <div class="title">Sorry, we&rsquo;re down for maintenance</div>
      <div class="content">We&rsquo;ll be back shortly</div>
    </div>
  </body>
</html>
```

## References

maintenance page

https://stackoverflow.com/questions/13693947/how-do-you-put-up-a-maintenance-page-for-aws-when-your-instances-are-behind-an-e
