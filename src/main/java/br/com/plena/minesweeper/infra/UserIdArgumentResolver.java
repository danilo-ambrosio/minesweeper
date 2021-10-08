package br.com.plena.minesweeper.infra;

import br.com.plena.minesweeper.domain.model.user.UserId;
import br.com.plena.minesweeper.domain.model.user.UserNotFoundException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(final MethodParameter methodParameter) {
    return methodParameter.getParameterType().equals(UserId.class);
  }

  @Override
  public Object resolveArgument(final MethodParameter methodParameter,
                                final ModelAndViewContainer modelAndViewContainer,
                                final NativeWebRequest nativeWebRequest,
                                final WebDataBinderFactory webDataBinderFactory) {
    final String userId = nativeWebRequest.getHeader("userId");
    if(userId == null) {
      throw new UserNotFoundException();
    }
    return UserId.of(userId);
  }
}
