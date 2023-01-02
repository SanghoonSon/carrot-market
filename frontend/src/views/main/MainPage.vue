<template>
  <v-main>
    <v-sheet class="py-16">
      <section id="filter">
        <v-container>
          <v-row justify="space-between">
            <v-col cols="auto">
              <v-responsive width="350">
                <h2 class="text-h4">
                  New customization system
                </h2>

                <p class="text-success mt-3">
                  Global Defaults
                </p>

                <strong class="mt-3">
                  Version 3 Only
                </strong>

                <p class="mt-8">
                  Vuetify 3 has an unprecedented level of customization options that make implementing any design system
                  easy.
                </p>

                <p class="mt-8">
                  Assign default values for all components in the library, including nested support.
                </p>

                <v-btn
                    class="mt-6"
                    href="https://next.vuetifyjs.com/features/global-configuration/"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                  More Information
                </v-btn>
              </v-responsive>
            </v-col>

            <v-img
                max-width="400"
                src="https://cdn.vuetifyjs.com/store/themes/vite-free/chips-bar.png"
            />
          </v-row>
        </v-container>
      </section>

      <v-container>
        <v-divider class="my-12"/>
      </v-container>

      <section id="controls">
        <v-container>
          <v-row justify="space-between">
            <v-col cols="auto">
              <v-responsive width="350">
                <h2 class="text-h4">
                  Rebuilt from the ground up
                </h2>

                <p class="text-success mt-3">
                  Composition API
                </p>

                <p class="mt-3">
                  Vuetify 3 uses the Vue composition API to build easy-to-use and feature rich components that work out
                  of the box.
                </p>

                <p class="mt-8">
                  <strong>How to use:</strong>

                  Services are now accessed through <strong>use functions</strong> that follow the Vue 3 nomenclature
                  and code styling.
                </p>

                <v-btn
                    class="mt-6"
                    href="https://next.vuetifyjs.com/introduction/why-vuetify/"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                  More Information
                </v-btn>
              </v-responsive>
            </v-col>

            <v-img
                max-width="400"
                src="https://cdn.vuetifyjs.com/store/themes/vite-free/slider.png"
            />
          </v-row>
        </v-container>
      </section>

      <v-container>
        <v-divider class="my-12"/>
      </v-container>

      <section id="grid">
        <v-container>
          <v-row justify="space-between">
            <v-col cols="auto">
              <v-responsive width="350">
                <h2 class="text-h4">
                  The most complete version yet
                </h2>

                <p class="text-success mt-3">
                  Available now!
                </p>

                <p class="mt-3">
                  The latest version is almost here. Use one of our free themes to get a head start!
                </p>

                <p class="mt-8">
                  This theme is designed to demonstrate a basic single page application using Vuetify 3.
                </p>

                <v-btn class="mt-6">
                  More Information
                </v-btn>
              </v-responsive>
            </v-col>

            <v-img
                max-width="400"
                src="https://cdn.vuetifyjs.com/store/themes/vite-free/layout.png"
            />
          </v-row>
        </v-container>
      </section>
    </v-sheet>

    <v-container>
      <v-row>
        <v-col
            v-for="n in 24"
            :key="n"
            cols="4"
        >
          <v-card height="200"></v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>
