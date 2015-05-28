/*
 * Template dsPIC33F
 * Compiler : Microchip xC16
 * �C : 33FJ128MC804
 * Mars 2015
 *
 *    ______ _____      _           _
 *   |___  /| ___ \    | |         | |
 *      / / | |_/ /___ | |__   ___ | |_
 *     / /  |    // _ \| '_ \ / _ \| __|
 *    / /   | |\ \ (_) | |_) | (_) | |_
 *   /_/    |_| \_\___/|____/ \___/'\__|
 *			      7robot.fr
 */



/********Headers********/
#include <xc.h>
/******************************************************************************/
/* Configuration                                                             */
/******************************************************************************/

// Select Oscillator and switching.
_FOSCSEL(FNOSC_FRCPLL & IESO_OFF);
// Select clock.
_FOSC(POSCMD_NONE & OSCIOFNC_ON & IOL1WAY_ON & FCKSM_CSDCMD);
// Watchdog Timer.
_FWDT(FWDTEN_OFF);
// Select debug channel.
_FICD(ICS_PGD3 & JTAGEN_OFF);

_FPOR(PWMPIN_ON);


#include <stdint.h>        // Includes uint16_t definition
#include <stdio.h>         // Includes sprintf
#include <stdbool.h>       // Includes true/false definition
#include <uart.h>
#include <math.h>
#include "user.h"          // User funct/params, such as InitApp
#include "qei.h"           // QEI definitions for easier use
#include <libpic30.h>
#include "ax12.h"
#include "motion.h"

#include "motor.h"
#include "actions_ax12.h"


int main(int argc, char** argv) {
    Init_All();
    int pwmGauche=0, pwmDroit=0;
    char sign=1;
    while (1) // boucle principale
    {
        if (pwmGauche==100 || pwmGauche==-100)
            sign*=-1;
        PWM_Moteurs(pwmGauche, pwmGauche);
        pwmGauche+=sign;
        __delay_ms(5);

    }
}

