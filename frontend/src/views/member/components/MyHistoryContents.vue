<template>
  <v-sheet
      min-height="70vh"
      rounded="lg"
  >
    <section id="controls">
      <v-container>
        <v-row justify="space-between">
          <v-col cols="auto">
            <v-responsive width="350">
              <h2 class="text-h4">
                히스토리
              </h2>

              <p class="text-success mt-3">
                히스토리
              </p>

              <p class="mt-3">
                Vuetify 3 uses the Vue composition API to build easy-to-use and feature rich components that work
                out
                of the box.
              </p>

              <p class="mt-8">
                <strong>How to use:</strong>

                Services are now accessed through <strong>use functions</strong> that follow the Vue 3
                nomenclature
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
  </v-sheet>
</template>

<script>
export default {
  name: 'ProfileContents',
  data: () => ({
    cards: ['Today', 'Yesterday'],
    drawer: null,
    links: [
      ['mdi-inbox-arrow-down', 'Inbox'],
      ['mdi-send', 'Send'],
      ['mdi-delete', 'Trash'],
      ['mdi-alert-octagon', 'Spam'],
    ],
  })
}
</script>
