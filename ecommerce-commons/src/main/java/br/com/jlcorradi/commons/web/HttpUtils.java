package br.com.jlcorradi.commons.web;

import br.com.jlcorradi.commons.auth.BasicJwtAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@RequiredArgsConstructor
enum HeaderMessageType
{
  ERROR("x-error-message"), INFO("x-info-message"), WARNING("x-warning-message"),
  SUCCESS("x-success-message");
  @Getter
  final String headerName;
}

/**
 * Utility class for working with HTTP-related tasks.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils
{

  public static Optional<String> getHeaderFromCurrentRequest(String headerName)
  {
    return getHttpServletRequest()
        .map(req -> req.getHeader(headerName));
  }

  /**
   * Gets the current HttpServletResponse associated with the request.
   *
   * @return The current HttpServletResponse instance.
   * @throws IllegalStateException If there is no active request.
   */
  public static Optional<HttpServletResponse> getHttpServletResponse()
  {
    return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
        .map(ServletRequestAttributes.class::cast)
        .map(ServletRequestAttributes::getResponse);
  }

  /**
   * Gets the current HttpServletResponse associated with the request.
   *
   * @return The current HttpServletResponse instance.
   * @throws IllegalStateException If there is no active request.
   */
  public static Optional<HttpServletRequest> getHttpServletRequest()
  {
    return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
        .map(ServletRequestAttributes.class::cast)
        .map(ServletRequestAttributes::getRequest);
  }

  /**
   * Adds a header message to the HttpServletResponse.
   *
   * @param messageType The type of the header message.
   * @param message     The message content to be added.
   */
  public static void addHeaderMessage(HeaderMessageType messageType, String message)
  {
    HttpUtils.getHttpServletResponse()
        .ifPresent(res -> res.addHeader(messageType.getHeaderName(), message));
  }

  /**
   * Retrieves the currently logged-in user details from the SecurityContextHolder.
   *
   * @return An Optional containing the logged-in user's details if available,
   * or an empty Optional if not logged in or details are not available.
   */
  public static Optional<BasicJwtAuthenticationToken> getLoggedinUser()
  {
    if (!BasicJwtAuthenticationToken.class.isAssignableFrom(
        SecurityContextHolder.getContext().getAuthentication().getClass())) {
      return Optional.empty();
    }
    return Optional.of(
        (BasicJwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
    );
  }
}