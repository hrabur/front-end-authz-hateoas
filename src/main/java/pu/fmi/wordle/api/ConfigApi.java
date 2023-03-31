package pu.fmi.wordle.api;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pu.fmi.wordle.logic.ConfigService;
import pu.fmi.wordle.model.security.Permissions;

@RestController
@RequestMapping("/api/config")
public class ConfigApi {

  final ConfigService configService;

  public ConfigApi(ConfigService configService) {
    this.configService = configService;
  }

  @GetMapping
  @RequiresPermissions(Permissions.CONFIG_READ)
  public ConfigModel getConfig() {
    return configService.getConfig();
  }

  @PostMapping
  @RequiresPermissions(Permissions.CONFIG_UPDATE)
  public ConfigModel updateConfig() {
    return null;
  }

}
