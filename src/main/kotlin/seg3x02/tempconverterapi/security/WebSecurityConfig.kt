package seg3x02.tempconverterapi.security


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService


@Configuration
class WebSecurityConfig {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    //manually stored login credentials
    @Bean
    fun userDetailsService(passwordEncoder: BCryptPasswordEncoder): UserDetailsService {
        val user1 = User.withUsername("test1")
            .password(passwordEncoder.encode("test1pass"))
            .roles("USER")
            .build()

        val user2 = User.withUsername("test2")
            .password(passwordEncoder.encode("test2pass"))
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(user1, user2)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/public/**").permitAll() // Allow public access to endpoints under /public
                    .anyRequest().authenticated()             // Require authentication for other requests
            }
            .httpBasic { it } // Use the lambda version of the Customizer
        return http.build()
    }


}
