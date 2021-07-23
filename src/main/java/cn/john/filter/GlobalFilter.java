package cn.john.filter;

import cn.hutool.core.net.NetUtil;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description: GlobalFilter
 * 限流过滤器
 * date: 2020/11/9 10:37
 * version: 1.0
 * @author John Yan
 */
@Slf4j
public class GlobalFilter implements Filter {

    /**
     * 桶的最大容量，即能装载 Token 的最大数量
     */
    int capacity = 10;
    /**
     * 每次 Token 补充量
     */
    int refillTokens = 1;

    /**
     * 补充 Token 的时间间隔
     */
    Duration duration = Duration.ofSeconds(1);


    private static final Map<String, Bucket> BUCKET_CACHE = new ConcurrentHashMap<>();

    private Bucket createNewBucket()
    {
        Refill refill = Refill.greedy(refillTokens, duration);
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public void init(FilterConfig filterConfig)  {
        log.info(filterConfig.getFilterName() + " init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String ip = NetUtil.getLocalhostStr();
        Bucket bucket = BUCKET_CACHE.computeIfAbsent(ip, k -> createNewBucket());
        long start = System.currentTimeMillis();
        log.info("IP: " + ip + "，has Tokens: " + bucket.getAvailableTokens());
        if (bucket.tryConsume(1)){
            filterChain.doFilter(request,response);
        }
        else{
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        log.info(request.getRequestURI()+"耗时:"+(System.currentTimeMillis() - start));
    }

    @Override
    public void destroy() {
        log.info( "destroy");
    }


    public GlobalFilter(int capacity, int refillTokens) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
    }
}
