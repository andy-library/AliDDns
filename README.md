# AliDDns
_家里搞了台群辉,不想用群辉自带的QuickConnect,自购了阿里云域名,路由器设置的是每天晚上自动断网重启,
再自己写个解析搞个镜像,完美解决,有需要的可以自己拿去用._


* 使用方式
> 系统变量设置  
> <kbd>REGION_ID</kbd>: 阿里云所使用的区域,例如:cn-hangzhou  
> <kbd>ACCESS_KEY</kbd>: 阿里云的access key,建议使用子账户并授权DNS解析权限  
> <kbd>ACCESS_SECRET</kbd>: 阿里云的access secret  
> <kbd>DOMAIN_NAME</kbd>: 自己要解析的域名  
> <kbd>CHECK_URL</kbd>: JSON方式获取公网IP的URL,忘记改代码了,目前只支持 https://jsonip.com 这个URL,有空再改一下代码  

* 启动命令（参考）  
> docker run -d -p port:port \  
> -e DOMAIN_NAME=域名 \  
> -e REGION_ID='cn-hangzhou' \  
> -e ACCESS_KEY=自己的Key \  
> -e ACCESS_SECRET=自己的secret \    
> -e CHECK_URL='https://jsonip.com' \  
> --name aliddns aliddns:1.0

* 执行时间
> 每15分钟执行一次