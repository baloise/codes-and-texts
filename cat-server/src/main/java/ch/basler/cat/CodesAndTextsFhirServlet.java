package ch.basler.cat;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ca.uhn.fhir.rest.api.EncodingEnum;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;


@WebServlet(urlPatterns = "/fhir/*", loadOnStartup = 1)
public class CodesAndTextsFhirServlet extends RestfulServer {

  private static final long serialVersionUID = -2808051765457821831L;

  @Value("${hapi.fhir.rest.default-response-encoding:json}")
  private EncodingEnum defaultResponseEncoding;

  @Value("${hapi.fhir.rest.default-pretty-print:false}")
  private boolean defaultPrettyPrint;

  @Autowired
  private void addResourceProviders(List<IResourceProvider> resourceProviders) {
    setResourceProviders(resourceProviders);
  }

  @Override
  public void initialize() {
    addInterceptors();
    setVariousConfig();
  }

  private void addInterceptors() {
    registerInterceptor(new ResponseHighlighterInterceptor());
  }

  private void setVariousConfig() {
    setDefaultResponseEncoding(defaultResponseEncoding);
    setDefaultPrettyPrint(defaultPrettyPrint);
  }
}
