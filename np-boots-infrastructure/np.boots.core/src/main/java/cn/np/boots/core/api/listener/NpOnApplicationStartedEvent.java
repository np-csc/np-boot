package cn.np.boots.core.api.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.event.ApplicationStartedEvent;

@Getter
@AllArgsConstructor
public class NpOnApplicationStartedEvent {
    private ApplicationStartedEvent event;
}
